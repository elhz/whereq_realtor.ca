import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { WhereQRealtorCaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ListingDetailComponent } from '../../../../../../main/webapp/app/entities/listing/listing-detail.component';
import { ListingService } from '../../../../../../main/webapp/app/entities/listing/listing.service';
import { Listing } from '../../../../../../main/webapp/app/entities/listing/listing.model';

describe('Component Tests', () => {

    describe('Listing Management Detail Component', () => {
        let comp: ListingDetailComponent;
        let fixture: ComponentFixture<ListingDetailComponent>;
        let service: ListingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhereQRealtorCaTestModule],
                declarations: [ListingDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ListingService,
                    EventManager
                ]
            }).overrideComponent(ListingDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ListingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Listing(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.listing).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
