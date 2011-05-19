package
{
	[Bindable] 
	public class PafIOVO
	{
		public var iotype:int;
		public var iototal:Number;
		public var iodate:String;
		public var staff:StaffVO;
		public var omemo:String;
		
		public function PafIOVO()
		{
			iototal = 0;
		}

	}
}