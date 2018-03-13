/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CarGatewayTestModule } from '../../../test.module';
import { DealerComponent } from '../../../../../../main/webapp/app/entities/dealer/dealer.component';
import { DealerService } from '../../../../../../main/webapp/app/entities/dealer/dealer.service';
import { Dealer } from '../../../../../../main/webapp/app/entities/dealer/dealer.model';

describe('Component Tests', () => {

    describe('Dealer Management Component', () => {
        let comp: DealerComponent;
        let fixture: ComponentFixture<DealerComponent>;
        let service: DealerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CarGatewayTestModule],
                declarations: [DealerComponent],
                providers: [
                    DealerService
                ]
            })
            .overrideTemplate(DealerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DealerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DealerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Dealer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dealers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
