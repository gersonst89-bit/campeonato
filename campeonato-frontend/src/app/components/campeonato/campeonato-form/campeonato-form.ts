import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CampeonatoService } from '../../../services/campeonato.service';
import { Campeonato } from '../../../models/campeonato';

@Component({
    selector: 'app-campeonato-form',
    standalone: true,
    imports: [ReactiveFormsModule, RouterLink],
    templateUrl: './campeonato-form.html'
})
export class CampeonatoForm implements OnInit {

    form: FormGroup;
    editando = false;
    idCampeonato: number | null = null;
    errorMsg = '';

    constructor(
        private fb: FormBuilder,
        private campeonatoService: CampeonatoService,
        private router: Router,
        private route: ActivatedRoute
    ) {
        this.form = this.fb.group({
            nombre: ['', Validators.required],
            fechaInicio: ['', Validators.required],
            cantidadEquipos: [null, [Validators.required, Validators.min(2)]]
        });
    }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.editando = true;
            this.idCampeonato = +id;
            this.campeonatoService.getById(+id).subscribe({
                next: (c: Campeonato) => this.form.patchValue({
                    nombre: c.nombre,
                    fechaInicio: c.fechaInicio,
                    cantidadEquipos: c.cantidadEquipos
                })
            });
        }
    }

    guardar(): void {
        if (this.form.invalid) return;

        const datos = this.form.value;
        datos.fechaInicio = datos.fechaInicio.split('T')[0];

        if (this.editando && this.idCampeonato) {
            this.campeonatoService.update(this.idCampeonato, datos).subscribe({
                next: () => this.router.navigate(['/campeonatos']),
                error: () => this.errorMsg = 'Error al actualizar el campeonato'
            });
        } else {
            this.campeonatoService.create(datos).subscribe({
                next: () => this.router.navigate(['/campeonatos']),
                error: () => this.errorMsg = 'Error al registrar el campeonato'
            });
        }
    }
}