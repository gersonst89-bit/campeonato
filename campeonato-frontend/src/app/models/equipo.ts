

export interface Equipo {
    idEquipo?: number;
    nombre: string;
    representante: string;
    fechaCreacion: string;
    idCampeonato: number;
    campeonato?: {
        id: number;
        nombre: string;
    };
}