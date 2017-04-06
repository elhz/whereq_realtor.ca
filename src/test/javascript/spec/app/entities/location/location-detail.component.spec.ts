import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { WhereQRealtorCaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LocationDetailComponent } from '../../../../../../main/webapp/app/entities/location/location-detail.component';
import { LocationService } from '../../../../../../main/webapp/app/entities/location/location.service';
import { Location } from '../../../../../../main/webapp/app/entities/location/location.model';

describe('Component Tests', () => {

    describe('Location Management Detail Component', () => {
        let comp: LocationDetailComponent;
        let fixture: ComponentFixture<LocationDetailComponent>;
        let service: LocationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhereQRealtorCaTestModule],
                declarations: [LocationDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LocationService,
                    EventManager
                ]
            }).overrideComponent(LocationDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Location(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.location).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
