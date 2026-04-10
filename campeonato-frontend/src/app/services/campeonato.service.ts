
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Campeonato } from '../models/campeonato';

@Injectable({ providedIn: 'root' })
export class CampeonatoService {

    private apiUrl = 'http://localhost:8080/api/campeonatos';

    constructor(private http: HttpClient) { }

    getAll(): Observable<Campeonato[]> {
        return this.http.get<Campeonato[]>(this.apiUrl);
    }

    getById(id: number): Observable<Campeonato> {
        return this.http.get<Campeonato>(`${this.apiUrl}/${id}`);
    }

    create(campeonato: Campeonato): Observable<Campeonato> {
        return this.http.post<Campeonato>(this.apiUrl, campeonato);
    }

    update(id: number, campeonato: Campeonato): Observable<Campeonato> {
        return this.http.put<Campeonato>(`${this.apiUrl}/${id}`, campeonato);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}