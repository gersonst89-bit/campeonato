import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Equipo } from '../models/equipo';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class EquipoService {

    private apiUrl = `${environment.apiUrl}/api/equipos`;

    constructor(private http: HttpClient) { }

    getAll(): Observable<Equipo[]> {
        return this.http.get<Equipo[]>(this.apiUrl);
    }

    getById(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${id}`);
    }

    getByCampeonato(campeonatoId: number): Observable<Equipo[]> {
        return this.http.get<Equipo[]>(`${this.apiUrl}/campeonato/${campeonatoId}`);
    }

    create(equipo: Equipo): Observable<Equipo> {
        return this.http.post<Equipo>(this.apiUrl, equipo);
    }

    update(id: number, equipo: any): Observable<any> {
        return this.http.put<any>(`${this.apiUrl}/${id}`, equipo);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}