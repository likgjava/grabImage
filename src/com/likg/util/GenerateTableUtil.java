package com.likg.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 导出表结构
 * @author likaige
 * @create 2015年7月10日 下午4:17:13
 */
public class GenerateTableUtil {

	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			conn = JdbcUtil.getConnection(JdbcUtil.DB_MYSQL, "devshop", "dev123456shop");
			List<Table> tableList = getTableList(conn);
			for(Table t : tableList){
				Table items = getTable(conn, t.getName());
				t.setItemList(items.getItemList());
				//System.out.println(t);
			}
			
			exportExcel(tableList);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(null, null, conn);
		}
	}
	
	public static void exportExcel(List<Table> tableList){
		try {
			Workbook wb = new SXSSFWorkbook();
			Sheet sheet = wb.createSheet();
			int rowNum = 0;
			int columnNum = 0;
			Row row = sheet.createRow(rowNum++);
			//表名称	字段	是否主键	是否外键/表	是否索引	类型/长度	备注

			row.createCell(columnNum++).setCellValue("表名称");
			row.createCell(columnNum++).setCellValue("表描述");
			row.createCell(columnNum++).setCellValue("字段");
			row.createCell(columnNum++).setCellValue("是否主键");
			row.createCell(columnNum++).setCellValue("是否外键/表");
			row.createCell(columnNum++).setCellValue("是否索引");
			row.createCell(columnNum++).setCellValue("类型/长度");
			row.createCell(columnNum++).setCellValue("备注");
			
			for(Table t : tableList){
				row = sheet.createRow(rowNum++);
				columnNum = 0;
				row.createCell(columnNum++).setCellValue(t.getName());
				row.createCell(columnNum++).setCellValue(t.getComment());
				int fieldCount = t.getItemList().size();
				for(int i=0; i<fieldCount; i++){
					TableItem item = t.getItemList().get(i);
					if(i > 0){
						row = sheet.createRow(rowNum++);
						columnNum = 2;
					}
					row.createCell(columnNum++).setCellValue(item.getField());
					row.createCell(columnNum++).setCellValue(item.isPK() ? "Y" : "");
					row.createCell(columnNum++).setCellValue("");
					row.createCell(columnNum++).setCellValue(item.isIndex() ? "Y" : "");
					row.createCell(columnNum++).setCellValue(item.getType());
					row.createCell(columnNum++).setCellValue(item.getComment());
				}
				
				System.out.println(t.getName());
				System.out.println(rowNum-fieldCount + "=="+(rowNum-1));
				sheet.addMergedRegion(new CellRangeAddress(rowNum-fieldCount, rowNum-1, 0, 0));
				sheet.addMergedRegion(new CellRangeAddress(rowNum-fieldCount, rowNum-1, 1, 1));
			}

			//sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
			FileOutputStream fos = new FileOutputStream("C:/Users/Administrator/Desktop/表结构.xlsx");
			wb.write(fos);
			
			wb.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Table getTable(Connection conn, String tableName) {
		Table table = new Table();
		try {
			String sql = "show full columns from "+tableName;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				TableItem t = new TableItem();
				t.setField(rs.getString("field"));
				t.setType(rs.getString("type"));
				t.setKey(rs.getString("key"));
				t.setComment(rs.getString("comment"));
				table.getItemList().add(t);
			}
			
			JdbcUtil.close(rs, ps, null);
		} catch (SQLException e) {
			//e.printStackTrace();
			System.err.println("出现异常的表："+tableName);
		}
		
		return table;
	}
	public static List<Table> getTableList(Connection conn) throws Exception{
		List<Table> tableList = new ArrayList<Table>();
		
		String sql = "SELECT t.table_name,t.table_comment FROM information_schema.tables t WHERE t.table_schema='shop'";
		//sql += " AND t.table_name like 'finance_p%'";
		sql += " AND t.table_name in('b2b_client','b2b_client_account','b2b_client_linkedman','b2b_client_productline','b2b_operation_log','b2b_pay_roll_sale_order','b2b_process_log','b2b_sale_order','b2b_sale_order_product','b2b_sale_return','b2b_sale_return_product','b2c_user_order','balance_compute','balance_corp_info','balance_cycle','balance_cycle_remark','balance_cycle_rule','balance_forecast_scale','balance_timepoint','buy_order','buy_order_accessary','buy_order_adjust','buy_order_adjust_history','buy_order_pay_history','buy_order_product','buy_plan','buy_plan_product','buy_return','buy_return_pay_status','buy_return_product','buy_return_warn_remind','buy_return_warn_remind_manage','buy_return_warn_reminder','buy_stock','buy_stock_measure_control','buy_stock_measure_control_detail','buy_stock_measure_control_detail_cache','buy_stock_product','claims_package_price','claims_verification','claims_verification_product','every_sku_monitor','finance_added_weight_special','finance_adjust_order','finance_after_sale_deal_log','finance_after_sale_proceeds','finance_after_sale_proceeds_detail','finance_after_sale_refund','finance_after_sale_refund_detail','finance_balance_stat','finance_balance_stat_detail','finance_bsby_stat','finance_buy_pay','finance_buy_product','finance_buy_stat0','finance_buy_stat1','finance_buy_stat_detail','finance_buyreturn_transinfo','finance_calibrate_application','finance_express','finance_express_augdt','finance_express_claim_rule','finance_express_province','finance_mailing_balance','finance_ms_taxrate','finance_order_deliver','finance_pre_pay','finance_product','finance_product_line','finance_product_line_catalog','finance_product_stock_log','finance_receive_aging','finance_receive_summary','finance_receive_summary_detail','finance_receive_unreceived','finance_sell','finance_sell_product','finance_sell_report_plan','finance_sell_stat','finance_sq_buystock','finance_stock_batch_history','finance_stock_batch_history_copy','finance_stock_card','finance_stock_card_statictis','finance_stock_stat','finance_sum_stock_batch','finance_sum_stock_batch_history','finance_warehousing_fee','first_balance_date','invoice_buy_order','invoice_certification','invoice_detail','invoice_group','invoice_info','invoice_purchase_consistency_ratio','invoice_real_differ_statistics','invoice_verification','mailing_balance','mailing_balance_auditing','mailing_balance_auditing_arrive','mailing_balance_auditing_log','mailing_balance_carriage','mailing_balance_estimated_charge','mailing_balance_temp','mailing_batch','mailing_batch_package','mailing_batch_parcel','mailing_charge_auditing','mailing_cost_log','mailing_express_balance','mailing_mailingorder_extend','mailing_service_charge','mailing_untread_auditing','order_id_cache','order_import_log','remote_fee_express','special_user_order','special_user_order_log','special_user_order_product_imperfections','special_user_order_product_sample','stock_batch','stock_batch_log','stock_batch_price','stock_batch_price_product','storage_warning_base_data','supplier_assist_material','supplier_badness_record_info','supplier_bank','supplier_bank_account','supplier_bill_find','supplier_buyer','supplier_buyreturn_mailinginfo','supplier_check_price_composition','supplier_check_price_list','supplier_check_price_season','supplier_contract_accessary','supplier_contract_edit','supplier_contract_ensurance','supplier_contract_info','supplier_contract_log','supplier_contract_money','supplier_contract_paytype','supplier_contract_productline','supplier_contract_return','supplier_factory','supplier_factory_offer_price','supplier_finance_info','supplier_grade','supplier_grade_apply_info','supplier_grade_apply_log','supplier_gram_weigth','supplier_imprest_application','supplier_imprest_application_log','supplier_imprest_info','supplier_imprest_pay_bank','supplier_imprest_proof','supplier_imprest_use_details','supplier_linkman','supplier_mailing_charge_finance_info','supplier_main_material','supplier_offer_attribute','supplier_operation_log','supplier_pay_application','supplier_pay_application_buy_order','supplier_pay_application_buy_return','supplier_pay_application_contract','supplier_pay_application_log','supplier_pay_application_payment_log','supplier_pay_application_upload_log','supplier_pay_contract_share','supplier_pre_pay_buy_order','supplier_pre_pay_supplier','supplier_price_catalogue','supplier_price_catalogue_log','supplier_process_price','supplier_product_composition','supplier_product_design','supplier_product_line','supplier_product_module','supplier_product_purchaser','supplier_product_purchaser_statictis','supplier_productline_purchaser','supplier_purchase_price_catalogue','supplier_purchaser_sku','supplier_quoted_price_catalogue','supplier_sample_plate_source','supplier_scope_width','supplier_shouhou_linkman','supplier_special_process','supplier_standard_info','supplier_standard_info_log','supplier_standard_info_test','supplier_user','supplier_user_product_line','supplier_verify_info','supplier_verify_log','supplier_wear_down','user_order_invoice','user_order_invoice_item','user_order_log')";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			Table t = new Table();
			t.setName(rs.getString("table_name"));
			t.setComment(rs.getString("table_comment"));
			tableList.add(t);
		}
		JdbcUtil.close(rs, ps, null);
		
		return tableList;
	}
	
}
