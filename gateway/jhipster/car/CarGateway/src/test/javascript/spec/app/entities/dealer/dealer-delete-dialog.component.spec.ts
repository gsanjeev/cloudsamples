/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CarGatewayTestModule } from '../../../test.module';
import { DealerDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/dealer/dealer-delete-dialog.component';
import { DealerService } from '../../../../../../main/webapp/app/entities/dealer/dealer.service';

describe('Component Tests', () => {

    describe('Dealer Management Delete Component', () => {
        let comp: DealerDeleteDialogComponent;
        let fixture: ComponentFixture<DealerDeleteDialogComponent>;
        let service: DealerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CarGatewayTestModule],
                declarations: [DealerDeleteDialogComponent],
                providers: [
                    DealerService
                ]
            })
            .overrideTemplate(DealerDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DealerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DealerService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
