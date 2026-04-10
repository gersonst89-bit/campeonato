import { Routes } from '@angular/router';
import { CampeonatoList } from './components/campeonato/campeonato-list/campeonato-list';
import { CampeonatoForm } from './components/campeonato/campeonato-form/campeonato-form';
import { EquipoList } from './components/equipo/equipo-list/equipo-list';
import { EquipoForm } from './components/equipo/equipo-form/equipo-form';
import { PartidoList } from './components/partido/partido-list/partido-list';
import { PartidoForm } from './components/partido/partido-form/partido-form';
import { Dashboard } from './components/dashboard/dashboard';

export const routes: Routes = [
    { path: '', component: Dashboard },   
    { path: 'dashboard', component: Dashboard },
    { path: 'campeonatos', component: CampeonatoList },
    { path: 'campeonatos/nuevo', component: CampeonatoForm },
    { path: 'equipos', component: EquipoList },
    { path: 'equipos/nuevo', component: EquipoForm },
    { path: 'partidos', component: PartidoList },
    { path: 'partidos/nuevo', component: PartidoForm },
    { path: 'partidos/editar/:id', component: PartidoForm },
    { path: 'equipos/editar/:id', component: EquipoForm },
    { path: 'campeonatos/editar/:id', component: CampeonatoForm }
];

