package fairy.valueobject.managers.transaction;

public class TransactionType {

	/*==================================== 0xa ======================================*/
	/*
	 * 		NAME			: TOKEN
	 * 		DESCRIPTION		: 네트워크 내 페어리 토큰 전송을 위한 트랜잭션
	 *		CODE			: 0xA001
	 * 		
	 *  	@param
	 *  
	 *  	-
	 */
	public static final short TOKEN = (short) 0xA001;
	
	/*
	 * 		NAME			: HYDROGEN
	 * 		DESCRIPTION		: 사용자의 수소량을 기록하기 위한 트랜잭션
	 *		CODE			: 0xA002
	 * 		
	 *  	@param
	 *  
	 *  	-
	 */
	public static final short HYDROGEN = (short) 0xA002;
	/*==================================== 0xa ======================================*/
	
	
	/*==================================== 0xc ======================================*/
	/*
	 * 		TITLE			: ORDER 
	 * 		DESCRIPTION		: 수소 경매 요청(주문)을 위한 트랜잭션
	 * 		CODE			: 0xC001	
	 * 
	 * 		@Param
	 * 
	 *  	buyerAddress: String				// 수소 구매자의 주소 값
	 *  	hydrogenType: short					// 부생 or NG 수소의 종류를 선택하는 트랜잭션
	 *  	maxTime: long						// 수소 경매의 최대 시간
	 */
	public static final short ORDER = (short) 0xC001;
	
	/*
	 * 		TITLE			: SELL_HYDROGEN 
	 * 		DESCRIPTION		: 수소 경매에 판매 등록을 위한 트랜잭션
	 * 		CODE			: 0xC002	
	 * 
	 * 		@Param
	 * 
	 *  	targetTransactionID: String				// ORDER 트랜잭션의 ID 값
	 *  	totalHydrogenCount: double				// 판매하게 될 수소의 총량
	 *  	value: double							// 수소 1단위당의 가격
	 *  	sellerAddress: String					// 수소 구매자의 주소 값
	 */
	public static final short SELL_HYDROGEN = (short) 0xC002;
	
	/*
	 * 		TITLE			: ORDER_CONFIRM 
	 * 		DESCRIPTION		: 수소 경매에 낙찰을 통해 끝난 경매 기록을 남기는 트랜잭션
	 * 		CODE			: 0xC003	
	 * 
	 * 		@Param
	 * 
	 *  	targetTransactionID: String				// ORDER 트랜잭션의 ID 값
	 *  	buyerAddress: String					// 구매하는 사람의 주소
	 *  	totalHydrogenCount: double				// 판매하게 될 수소의 총량
	 *  	value: double							// 수소 1단위당의 가격
	 *  	sellerAddress: String					// 수소 구매자의 주소 값
	 */
	public static final short ORDER_CONFIRM = (short) 0xC003;
	/*==================================== 0xc ======================================*/
	
	
	/*==================================== 0xd ======================================*/
	/*
	 * 		TITLE			: DELIVERY
	 * 		DESCRIPTION		: 수소의 배달 경로를 기록하는 트랜잭션
	 * 		CODE			: 0xD001
	 * 
	 * 		@Param
	 * 
	 *  	startPoisition: String					// 배달의 시작지점을 기록한 트랜잭션
	 *  	endPosition: String						// 배달의 목표지점을 기록한 트랜잭션
	 *  	hydrogenValue: double					// 배달하는 수소의 총량
	 */
	public static final short DELIVERY = (short) 0xD001;
	/*==================================== 0xd ======================================*/
	
	
}
