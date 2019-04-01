package fairy.valueobject.managers.transaction;

public class TransactionType {

	/*==================================== 0xa ======================================*/
	/*
	 * 		NAME			: TOKEN
	 * 		DESCRIPTION		: ��Ʈ��ũ �� �� ��ū ������ ���� Ʈ�����
	 *		CODE			: 0xA001
	 * 		
	 *  	@param
	 *  
	 *  	-
	 */
	public static final short TOKEN = (short) 0xA001;
	
	/*
	 * 		NAME			: HYDROGEN
	 * 		DESCRIPTION		: ������� ���ҷ��� ����ϱ� ���� Ʈ�����
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
	 * 		DESCRIPTION		: ���� ��� ��û(�ֹ�)�� ���� Ʈ�����
	 * 		CODE			: 0xC001	
	 * 
	 * 		@Param
	 * 
	 *  	buyerAddress: String				// ���� �������� �ּ� ��
	 *  	hydrogenType: short					// �λ� or NG ������ ������ �����ϴ� Ʈ�����
	 *  	maxTime: long						// ���� ����� �ִ� �ð�
	 */
	public static final short ORDER = (short) 0xC001;
	
	/*
	 * 		TITLE			: SELL_HYDROGEN 
	 * 		DESCRIPTION		: ���� ��ſ� �Ǹ� ����� ���� Ʈ�����
	 * 		CODE			: 0xC002	
	 * 
	 * 		@Param
	 * 
	 *  	targetTransactionID: String				// ORDER Ʈ������� ID ��
	 *  	totalHydrogenCount: double				// �Ǹ��ϰ� �� ������ �ѷ�
	 *  	value: double							// ���� 1�������� ����
	 *  	sellerAddress: String					// ���� �������� �ּ� ��
	 */
	public static final short SELL_HYDROGEN = (short) 0xC002;
	
	/*
	 * 		TITLE			: ORDER_CONFIRM 
	 * 		DESCRIPTION		: ���� ��ſ� ������ ���� ���� ��� ����� ����� Ʈ�����
	 * 		CODE			: 0xC003	
	 * 
	 * 		@Param
	 * 
	 *  	targetTransactionID: String				// ORDER Ʈ������� ID ��
	 *  	buyerAddress: String					// �����ϴ� ����� �ּ�
	 *  	totalHydrogenCount: double				// �Ǹ��ϰ� �� ������ �ѷ�
	 *  	value: double							// ���� 1�������� ����
	 *  	sellerAddress: String					// ���� �������� �ּ� ��
	 */
	public static final short ORDER_CONFIRM = (short) 0xC003;
	/*==================================== 0xc ======================================*/
	
	
	/*==================================== 0xd ======================================*/
	/*
	 * 		TITLE			: DELIVERY
	 * 		DESCRIPTION		: ������ ��� ��θ� ����ϴ� Ʈ�����
	 * 		CODE			: 0xD001
	 * 
	 * 		@Param
	 * 
	 *  	startPoisition: String					// ����� ���������� ����� Ʈ�����
	 *  	endPosition: String						// ����� ��ǥ������ ����� Ʈ�����
	 *  	hydrogenValue: double					// ����ϴ� ������ �ѷ�
	 */
	public static final short DELIVERY = (short) 0xD001;
	/*==================================== 0xd ======================================*/
	
	
}
