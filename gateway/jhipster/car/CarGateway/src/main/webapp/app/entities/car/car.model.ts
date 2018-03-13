import { BaseEntity } from './../../shared';

export class Car implements BaseEntity {
    constructor(
        public id?: number,
        public make?: string,
        public model?: string,
        public price?: number,
    ) {
    }
}
