export class Location {
    constructor(
        public id?: number,
        public address?: string,
        public latitude?: number,
        public longitude?: number,
        public postcode?: string,
        public fullAddress?: string,
        public description?: string,
    ) {
    }
}
