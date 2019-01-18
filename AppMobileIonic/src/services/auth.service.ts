import { STORAGE_KEYS } from './../config/storage_keys.config';
import { LocalUser } from './../models/local_user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";

import { JwtHelper } from 'angular2-jwt';
import { API_CONFIG } from './../config/api.config';
import { CredenciaisDTO } from './../models/credenciais.dto';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class AuthService {

    jwtHelper: JwtHelper = new JwtHelper();

    constructor(public http: HttpClient){}

    authenticate(credenciais: CredenciaisDTO): Observable<any>{
        // O 'observe: response' especifica que a requisição retorna um objeto do tipo response. Dessa forma, poderemos acessar o header
        return this.http.post( `${API_CONFIG.baseUrl}/login`, credenciais, {observe: 'response', responseType: 'text'});
    }

    successfulLogin(authorizationValue: string) {
        let token = authorizationValue.substring(7);
        let user : LocalUser = {
            token: token,
            email: this.jwtHelper.decodeToken(token).sub
        };
        localStorage.setItem(STORAGE_KEYS.localUser, JSON.stringify(user));
    }

    logout() {
    }
}