import { Fixture } from "./fixture.interface";
import { Giocatore } from "./giocatore.interface";
import { Team } from "./team.interface";

export interface GiocatoriVotiNellePartite {
   id: number;
  giocatore: Giocatore;
  team: Team;
  fixture: Fixture;
  rating: number | null;
}
