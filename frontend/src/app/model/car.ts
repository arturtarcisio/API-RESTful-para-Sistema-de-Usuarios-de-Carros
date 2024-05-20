import { User } from "./user";

export interface Car {
    id?:                  any;
    carYear:              number | null;
    licensePlate:         string;
    model:                string;
    color:                string;
    //user:               User
  }