package br.ufma.lsdi.util.mail;

public abstract class MailBody {
    public static final String RECUPERAR_SENHA_PT1 = "<table bgcolor=\"edf7f9\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "<tr>" +
            "<td align=\"center\">\n" +
            "<table bgcolor=\"333333\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #333333; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>" +
            "<td>" +
            "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td height=\"10\"></td>\n" +
            "<td height=\"10\"></td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td align=\"left\" style=\"color: #cccccc; font-family: Helvetica,Arial,sans-serif; font-size: 13px; line-height: 13px;\">\n" +
            "<a href=\"http://www.seap.ma.gov.br/\" target=\"_blank\" style=\"color: #cccccc; text-decoration: none;\">SECRETARIA DE ESTADO DE ADMINISTRAÇÃO PENITENCIÁRIA - SEAP</a>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td height=\"10\"></td>\n" +
            "<td height=\"10\"></td>" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<table bgcolor=\"edf7f9\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #edf7f9; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td>\n" +
            "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\"> \n" +
            "<tr>\n" +
            "<td height=\"15\"></td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td align=\"center\">\n" +
            "<table border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" width=\"250\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td align=\"right\" valign=\"top\" style=\"color: #ffffff; font-family: Helvetica,Arial,sans-serif; font-size: 14px; line-height: 18px; font-weight: 300;\">\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td height=\"10\"></td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<table border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" width=\"250\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td align=\"left\">\n" +
            "<img src=\"http://www.seap.ma.gov.br/files/2016/04/LOGO-SEAP-MA.jpg\" alt=\"brasao\" width=\"80\" border=\"0\" style=\"width: 80px; border-style: none; display: block;\"/>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td height=\"5\"></td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<table bgcolor=\"edf7f9\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #edf7f9; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td height=\"20\" style=\"height: 20px;\"></td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<table border=\"0\" align=\"center\" bgcolor=\"edf7f9\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #edf7f9; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td>\n" +
            "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" bgcolor=\"29aeec\" style=\"background-color: #29aeec; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td height=\"30\">&nbsp;</td>\n" +
            "<td>&nbsp;</td>\n" +
            "<td>&nbsp;</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td width=\"25\">&nbsp;</td>\n" +
            "<td style=\"color: #FFFFFF; font-family: Helvetica,Arial,sans-serif; font-size: 16px; line-height: 22px; font-weight: 300;\">\n" +
            "<table border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td width=\"10\">&nbsp;</td>\n" +
            "<td align=\"left\" style=\"color: #FFFFFF; font-family: Helvetica,Arial,sans-serif; font-size: 16px; line-height: 22px; font-weight: 600;\">Caro servidor" +
            ",</td>\n" +
            "<td width=\"10\">&nbsp;</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td width=\"10\">&nbsp;</td>\n" +
            "<td height=\"10\">&nbsp;</td>\n" +
            "<td width=\"10\">&nbsp;</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td width=\"10\">&nbsp;</td>\n" +
            "<td align=\"left\" style=\"color: #FFFFFF; font-family: Helvetica,Arial,sans-serif; font-size: 16px; line-height: 22px; font-weight: 300;\">\n" +
            "<p>Segue sua nova senha de acesso aos sistemas da SEAP:\n" +
            "<br/>Usuário:";

    public static final String RECUPERAR_SENHA_PT2 = "<br/>\nSenha  : ";

    public static final String RECUPERAR_SENHA_PT3 = " (Senha Padrão, deve ser alterada no primeiro acesso)\n" +
            "<br/>Link: https://Ar Limpo.ma.gov.br/\n<br/><br/>" +
            "<br/></br>Atenciosamente,\n" +
            "<br/><br/>Desenvolvimento de Sistemas\n" +
            "<br/>Secretaria de Estado da Administração Penitenciária." +
            "</p>\n" +
            "</td>\n" +
            "<td width=\"10\">&nbsp;</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "<td width=\"25\">&nbsp;</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td height=\"30\">&nbsp;</td>\n" +
            "<td>&nbsp;</td>\n" +
            "<td>&nbsp;</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td height=\"10\">&nbsp;</td>\n" +
            "<td>&nbsp;</td>\n" +
            "<td>&nbsp;</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<table bgcolor=\"edf7f9\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #edf7f9; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td>\n" +
            "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td height=\"10\">&nbsp;</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td align=\"center\">\n" +
            "<img src=\"http://www.seap.ma.gov.br/files/2016/05/logo-seap-2016.png\" alt=\"seap\" width=\"400\" border=\"0\" style=\"width: 400px !important; border-style: none; display: block;\"/>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td height=\"10\">&nbsp;</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<table bgcolor=\"edf7f9\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #edf7f9; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td>\n" +
            "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\">\n" +
            "<tr>\n" +
            "<td height=\"10\"></td>\n" +
            "<td></td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td align=\"center\" style=\"color: #666666; font-family: Helvetica,Arial,sans-serif; font-size: 13px; line-height: 20px;\">\n" +
            "<a href=\"https://www.google.com.br/maps/@-2.5506911,-44.2586171,790m/data=!3m1!1e3?hl=pt-BR\" target=\"_blank\" style=\"color: #29aeec; text-decoration: none;\">Ver no Google Maps</a><br/>\n" +
            "<p>Qualquer dúvida entre em contato conosco: <br/>\n" +
            "<b>Site:</b>\n" +
            "<a href=\"http://www.seap.ma.gov.br/\" target=\"_blank\" style=\"color: #333333\">seap.ma.gov.br</a> | <b>Email:</b>\n" +
            "<a href=\"mailto:desenvolvimento@seap.ma.gov.br\" style=\"color: #333333\">desenvolvimento@seap.ma.gov.br</a>\n" +
            "</p>\n" +
            "</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td height=\"10\"></td>\n" +
            "<td></td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>";
}
