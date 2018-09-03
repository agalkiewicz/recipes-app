import {User} from "./user";

export interface SignInProvider {
  initialize(): Promise<User>;

  signIn(): Promise<User>;

  signOut(): Promise<any>;
}
