import { Car } from "./car"

export interface UserInfo {
    firstName: string
    lastName:  string
    email:     string
    birthday:  Date | null
    login:     string    
    phone:     string
    cars: Car[]
    createAt:  Date | null
    lastLogin: Date | null
}