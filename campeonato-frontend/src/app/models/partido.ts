
export interface Partido {
    id?: number;
    equipoLocalId: number;
    equipoVisitanteId: number;
    golesLocal: number;
    golesVisitante: number;
    fecha: string;
    idCampeonato: number;
}

export interface PartidoResponse {
    id: number;
    equipoLocalNombre: string;
    equipoVisitanteNombre: string;
    golesLocal: number;
    golesVisitante: number;
    fecha: string;
    campeonatoNombre: string;
}

export interface PosicionTabla {
    idEquipo: number;
    nombreEquipo: string;
    pj: number;
    g: number;
    e: number;
    p: number;
    gf: number;
    gc: number;
    pts: number;
    get diferenciaGoles(): number; 
}

