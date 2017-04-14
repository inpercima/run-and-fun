describe('activityService', () => {

  let activityService;
  let $httpBackend;

  beforeEach(module('app'));
  beforeEach(inject(function(_activityService_, $injector) {
    activityService = _activityService_;
    $httpBackend = $injector.get('$httpBackend');
    $httpBackend.whenGET('/state').respond(200, '');
  }));

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  describe('list', () => {
    let params = {
      foo: 'foo',
      bar: 'bar',
    };

    it('should list', () => {
      $httpBackend.whenGET('/listActivities?foo=foo&bar=bar').respond(200, '');
      $httpBackend.expectGET('/listActivities?foo=foo&bar=bar');
      expect(activityService.list(params)).not.toBe(null);
      $httpBackend.flush();
    });
  });

  describe('indexActivities', () => {
    let count = 12;

    it('should indexActivities', () => {
      $httpBackend.whenGET('/indexActivities').respond(200, count);
      $httpBackend.expectGET('/indexActivities');
      activityService.indexActivities().then(function(data) {
        expect(data).toEqual(12);
      });
      $httpBackend.flush();
    });
  });
});
