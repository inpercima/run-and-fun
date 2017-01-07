describe('activityService', function () {

  var activityService;

  beforeEach(module('app'));
  beforeEach(inject(function (_activityService_) {
    activityService = _activityService_;
  }));

  describe('addParams', function () {

    let addParams1 = {
      foo: 'foo',
      bar: 'bar',
    };

    let addParams2 = {
      foo: 'long',
      bar: 'other',
      woo: 'woo',
    };

    it('should addParams', function () {
      expect(activityService.addParams(addParams1)).toEqual('?foo=foo&bar=bar');
      expect(activityService.addParams(addParams2)).toEqual('?foo=long&bar=other&woo=woo');
    });

  });

  describe('getTotalTime', function () {

    let getTotalTime1 = [
      { duration: '00:01:11' },
    ];

    let getTotalTime2 = [
      { duration: '00:01:11' },
      { duration: '00:01:11' },
    ];

    it('should getTotalTime', function () {
      expect(activityService.getTotalTime(getTotalTime1)).toEqual(71);
      expect(activityService.getTotalTime(getTotalTime2)).toEqual(142);
    });

  });

  describe('getTotalDistance', function () {

    let getTotalDistance1 = [
      { distance: 1 },
      { distance: 2 },
      { distance: 3 },
    ];

    let getTotalDistance2 = [
      { distance: 0 },
      { distance: 1 },
      { distance: 2 },
    ];

    it('should getTotalDistance', function () {
      expect(activityService.getTotalDistance(getTotalDistance1)).toEqual('6.00');
      expect(activityService.getTotalDistance(getTotalDistance2)).toEqual('3.00');
    });

  });

});

