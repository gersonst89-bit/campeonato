
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Partido, PartidoResponse, PosicionTabla } from '../models/partido';

@Injectable({ providedIn: 'root' })
export class PartidoService {

    private apiUrl = 'http://localhost:8080/api/partidos';

    constructor(private http: HttpClient) { }

    getAll(): Observable<PartidoResponse[]> {
        return this.http.get<PartidoResponse[]>(this.apiUrl);
    }

    getById(id: number): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/${id}`);
    }

    getByCampeonato(campeonatoId: number): Observable<PartidoResponse[]> {
        return this.http.get<PartidoResponse[]>(`${this.apiUrl}/campeonato/${campeonatoId}`);
    }

    getTabla(campeonatoId: number): Observable<PosicionTabla[]> {
        return this.http.get<PosicionTabla[]>(`${this.apiUrl}/tabla/${campeonatoId}`);
    }

    create(partido: Partido): Observable<PartidoResponse> {
        return this.http.post<PartidoResponse>(this.apiUrl, partido);
    }

    update(id: number, data: any): Observable<any> {
        return this.http.put<any>(`${this.apiUrl}/${id}`, data);
    }
    
    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}