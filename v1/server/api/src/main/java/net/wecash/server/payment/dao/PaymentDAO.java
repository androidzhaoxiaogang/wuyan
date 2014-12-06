package net.wecash.server.payment.dao;

import net.wecash.server.mysql.model.Payment;


public interface PaymentDAO {

	public boolean validateUserBill(Long userId);

	public Payment getPayment(Long userId);

}
