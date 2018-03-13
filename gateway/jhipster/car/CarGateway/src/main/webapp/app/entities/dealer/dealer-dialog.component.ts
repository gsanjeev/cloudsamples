import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Dealer } from './dealer.model';
import { DealerPopupService } from './dealer-popup.service';
import { DealerService } from './dealer.service';

@Component({
    selector: 'jhi-dealer-dialog',
    templateUrl: './dealer-dialog.component.html'
})
export class DealerDialogComponent implements OnInit {

    dealer: Dealer;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dealerService: DealerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dealer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dealerService.update(this.dealer));
        } else {
            this.subscribeToSaveResponse(
                this.dealerService.create(this.dealer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Dealer>>) {
        result.subscribe((res: HttpResponse<Dealer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Dealer) {
        this.eventManager.broadcast({ name: 'dealerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-dealer-popup',
    template: ''
})
export class DealerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dealerPopupService: DealerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dealerPopupService
                    .open(DealerDialogComponent as Component, params['id']);
            } else {
                this.dealerPopupService
                    .open(DealerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
