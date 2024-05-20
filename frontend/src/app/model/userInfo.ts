import { Car } from "./car"

export interface UserInfo {
    firstName: string
    lastName:  string
    email:     string
    birthday:  string
    login:     string    
    phone:     string
    cars: Car[]
    createAt:  string
    lastLogin: string
}