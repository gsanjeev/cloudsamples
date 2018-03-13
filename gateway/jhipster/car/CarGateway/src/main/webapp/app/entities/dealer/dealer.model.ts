import { BaseEntity } from './../../shared';

export class Dealer implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public location?: string,
    ) {
    }
}
