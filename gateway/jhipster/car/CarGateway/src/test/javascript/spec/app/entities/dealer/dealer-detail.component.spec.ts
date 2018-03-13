/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CarGatewayTestModule } from '../../../test.module';
import { DealerDetailComponent } from '../../../../../../main/webapp/app/entities/dealer/dealer-detail.component';
import { DealerService } from '../../../../../../main/webapp/app/entities/dealer/dealer.service';
import { Dealer } from '../../../../../../main/webapp/app/entities/dealer/dealer.model';

describe('Component Tests', () => {

    describe('Dealer Management Detail Component', () => {
        let comp: DealerDetailComponent;
        let fixture: ComponentFixture<DealerDetailComponent>;
        let service: DealerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CarGatewayTestModule],
                declarations: [DealerDetailComponent],
                providers: [
                    DealerService
                ]
            })
            .overrideTemplate(DealerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DealerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DealerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Dealer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dealer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
