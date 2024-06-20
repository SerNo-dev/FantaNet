import { AuthData } from "./auth-data.interface";

export interface Match {
    id: number;
    user1: AuthData;
    user2: AuthData;
    user1Score: number;
    user2Score: number;
    matchDate: Date;
    winner: AuthData;
}
