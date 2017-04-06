
const enum ListingType {
    'CONDOMINIUMS',
    'DETACHED',
    'TOWNHOUSE',
    'SEMI_DETACHED',
    'DUPLEX_TRIPLEX',
    'COMMERCIAL_PROPERTY'

};

const enum ListingStatus {
    'AVAILABLE',
    'UNAVAILABLE'

};

const enum Style {
    'BUNGALOW',
    'THREESTOREY',
    'TWOSTOREY'

};
import { Location } from '../location';
export class Listing {
    constructor(
        public id?: number,
        public listingType?: ListingType,
        public status?: ListingStatus,
        public inputDate?: any,
        public modifiedDate?: any,
        public style?: Style,
        public listingPrice?: number,
        public location?: Location,
    ) {
    }
}
