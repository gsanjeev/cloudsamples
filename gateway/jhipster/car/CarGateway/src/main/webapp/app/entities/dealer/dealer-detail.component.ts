import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Dealer } from './dealer.model';
import { DealerService } from './dealer.service';

@Component({
    selector: 'jhi-dealer-detail',
    templateUrl: './dealer-detail.component.html'
})
export class DealerDetailComponent implements OnInit, OnDestroy {

    dealer: Dealer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dealerService: DealerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDealers();
    }

    load(id) {
        this.dealerService.find(id)
            .subscribe((dealerResponse: HttpResponse<Dealer>) => {
                this.dealer = dealerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDealers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dealerListModification',
            (response) => this.load(this.dealer.id)
        );
    }
}
