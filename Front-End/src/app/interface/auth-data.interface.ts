import { Giocatore } from "./giocatore.interface";

export interface AuthData {
  id: number;
  username: string;
  email: string;
  accessToken: string;
  crediti: number;
  avatar: string;
  carrello: Giocatore[];
  deck: Giocatore[];
  giocatoriAcquistati: Giocatore[];
  avatarUrl:string;
}