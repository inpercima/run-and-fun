describe('dateTimeUtils', () => {

  let dateTimeUtils;

  beforeEach(module('app'));
  beforeEach(inject(function(_dateTimeUtils_) {
    dateTimeUtils = _dateTimeUtils_;
  }));

  describe('formattedTimeToSeconds', () => {
    it('should formattedTimeToSeconds', () => {
      expect(dateTimeUtils.formattedTimeToSeconds('00:01:11')).toEqual(71);
      expect(dateTimeUtils.formattedTimeToSeconds('00:00:01')).toEqual(1);
      expect(dateTimeUtils.formattedTimeToSeconds('01:00:00')).toEqual(3600);
      expect(dateTimeUtils.formattedTimeToSeconds('01:01:01')).toEqual(3661);
    });
  });

  describe('formatTime', () => {
    it('should formatTime', () => {
      expect(dateTimeUtils.formatTime(71)).toEqual('00:01:11');
      expect(dateTimeUtils.formatTime(1)).toEqual('00:00:01');
      expect(dateTimeUtils.formatTime(3600)).toEqual('01:00:00');
      expect(dateTimeUtils.formatTime(3661)).toEqual('01:01:01');
    });
  });
});
