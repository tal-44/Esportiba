export class User {

    constructor(
        public id: number | null,
        public username: string,
        public firstName: string,
        public lastName: string,
        public email: string,
        public address: string,
        public cellphone: string,
        public password: string,
        public userType: string,

    ) {}

    getFullName(): string {
        return `${this.firstName} ${this.lastName}`;
    }

}
