import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Route, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import {
  HTTP_INTERCEPTORS,
  HttpClientJsonpModule,
  HttpClientModule,
} from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MainComponent } from './components/main/main.component';
import { Card1Component } from './components/card1/card1.component';
import { DescriptionComponent } from './components/description/description.component';
import { FooterComponent } from './components/footer/footer.component';
import { ProfileComponent } from './components/profile/profile.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PlayersComponent } from './components/players/players.component';
import { CarrelloComponent } from './components/carrello/carrello.component';
import { TokenInterceptor } from './auth/token.interceptor';
import { AuthService } from './auth/auth.service';
import { CommonModule } from '@angular/common';
import { AuthGuard } from './auth/auth.guard';
import { DeckComponent } from './components/deck/deck.component';
import { GameComponent } from './components/game/game.component';

import { SocketIoModule, SocketIoConfig } from 'ngx-socket-io';
import { ReplayComponent } from './components/replay/replay.component';



const config: SocketIoConfig = { url: 'http://localhost:8081', options: {} };
const routes: Route[] = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'accedi',
    component: LoginComponent,
  },
  {
    path: 'registrati',
    component: RegisterComponent,
  },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      { path: 'players', component: PlayersComponent },
      { path: 'game', component: GameComponent,canActivate: [AuthGuard] },
      { path: 'deck', component: DeckComponent, canActivate: [AuthGuard] },
      { path: 'replay', component: ReplayComponent }
]},  { path: 'carrello', component: CarrelloComponent, canActivate: [AuthGuard] },
// { path: 'deck', component: DeckComponent, canActivate: [AuthGuard] },
{ path: '**', redirectTo: '' }
];
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    NavbarComponent,
    MainComponent,
    Card1Component,
    DescriptionComponent,
    FooterComponent,
    ProfileComponent,
    DashboardComponent,
    PlayersComponent,
    CarrelloComponent,
    DeckComponent,
    GameComponent,
    ReplayComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    CommonModule,
    SocketIoModule.forRoot(config)
  ],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
