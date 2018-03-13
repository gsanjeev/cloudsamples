import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Dealer } from './dealer.model';
import { DealerPopupService } from './dealer-popup.service';
import { DealerService } from './dealer.service';

@Component({
    selector: 'jhi-dealer-delete-dialog',
    templateUrl: './dealer-delete-dialog.component.html'
})
export class DealerDeleteDialogComponent {

    dealer: Dealer;

    constructor(
        private dealerService: DealerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dealerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dealerListModification',
                content: 'Deleted an dealer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dealer-delete-popup',
    template: ''
})
export class DealerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dealerPopupService: DealerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dealerPopupService
                .open(DealerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
