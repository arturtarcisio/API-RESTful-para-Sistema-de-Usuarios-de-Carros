import { Car } from "./car"

export interface User {
    id?:       any
    firstName: string
    lastName:  string
    email:     string
    birthday:  string
    login:     string
    password:  string
    phone:     string
    //cars: Car[]
}