//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (C) 2008-2009 Openbravo, S.L.
//    http://www.unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.payment;

/**
 *
 * @author JG uniCenta
 */
public class PaymentInfoMagcardRefund extends PaymentInfoMagcard {
    
    /** Creates a new instance of PaymentInfoMagcardRefund
     * @param sHolderName
     * @param track3
     * @param sExpirationDate
     * @param sCardNumber
     * @param track2
     * @param track1
     * @param dTotal
     * @param sTransactionID */
    public PaymentInfoMagcardRefund(String sHolderName, String sCardNumber, String sExpirationDate, String track1, String track2, String track3, String sTransactionID, double dTotal) {
       super(sHolderName, sCardNumber, sExpirationDate, track1, track2, track3, sTransactionID, dTotal);
    }
    
    /** Creates a new instance of PaymentInfoMagcard
     * @param sHolderName
     * @param sCardNumber
     * @param sExpirationDate
     * @param dTotal
     * @param sTransactionID */
    public PaymentInfoMagcardRefund(String sHolderName, String sCardNumber, String sExpirationDate, String sTransactionID, double dTotal) {
        super(sHolderName, sCardNumber, sExpirationDate, sTransactionID, dTotal);
    }
    
    /**
     *
     * @return
     */
    @Override
    public PaymentInfo copyPayment(){
        PaymentInfoMagcard p = new PaymentInfoMagcardRefund(m_sHolderName, m_sCardNumber, m_sExpirationDate, track1, track2, track3, m_sTransactionID, m_dTotal);
        p.m_sAuthorization = m_sAuthorization;
        p.m_sErrorMessage = m_sErrorMessage;
        return p;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return "magcardrefund";
    }    
}
