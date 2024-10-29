package com.example.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.app.domain.BookingFormItem;

@WebMvcTest
class BookingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Test
	void test() throws Exception {
		var bookingFormItem = new BookingFormItem();
		//あらかじめ「駐車場を利用しない」を設定
		bookingFormItem.setParking(2);
		
		//ここからテスト本編
		mockMvc.perform(get("/booking"))
		.andExpect(status().isOk())
		.andExpect(view().name("booking"))
		.andExpect(model().attributeExists("roomTypeList"))
		;	}
	
	@Test
	void testBookingPostTrue() throws Exception {
		//nameがnullとして設定されるケース
		   mockMvc.perform(post("/booking")
	                
	                .param("email", "hoge@gmail.com")
	                .param("number", "3")
	                .param("roomType.id", "1")
	                .param("date", "2014-12-31")
	                .param("time", "1")
	                .param("agreement", "true"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("bookingDone"))
	                .andExpect(model().attributeHasFieldErrors("bookingFormItem", "name"));
		   //nameが" "として設定されるケース
		   mockMvc.perform(post("/booking")
	                .param("name", " ")
	                .param("email", "hoge@gmail.com")
	                .param("number", "3")
	                .param("roomType.id", "1")
	                .param("date", "2014-12-31")
	                .param("time", "1")
	                .param("agreement", "true"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("bookingDone"))
	                .andExpect(model().attributeHasFieldErrors("bookingFormItem", "name"));
		   
		   //nameが10文字を超えるケース
		   mockMvc.perform(post("/booking")
	                .param("name", "hogefugapiyo")
	                .param("email", "hoge@gmail.com")
	                .param("number", "3")
	                .param("roomType.id", "1")
	                .param("date", "2014-12-31")
	                .param("time", "1")
	                .param("agreement", "true"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("bookingDone"))
	                .andExpect(model().attributeHasFieldErrors("bookingFormItem", "name"));
		   
		   mockMvc.perform(post("/booking")
	                .param("name", "hogehabcde")
	                .param("email", "hoge@gmail.com")
	                .param("number", "3")
	                .param("roomType.id", "1")
	                .param("date", "2025-12-31")
	                .param("time", "1")
	                .param("agreement", "true"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("bookingDone"))
	                .andExpect(model().attributeHasNoErrors("bookingFormItem"));
		   
		   mockMvc.perform(post("/booking")
	                .param("name", "hoge")
	                .param("email", "hoge@gmail.com")
	                .param("number", "3")
	                .param("roomType.id", "1")
	                .param("date", "2025-12-31")
	                .param("time", "1")
	                .param("agreement", "true"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("bookingDone"))
	               .andExpect(model().attributeHasNoErrors("bookingFormItem"));
	}
	
//	
//	//部屋の種類のリストを返すメソッド
//		private List<RoomType> getRoomTypeList() {
//			List<RoomType> roomTypeList = new ArrayList<>();
//			 roomTypeList.add(new RoomType(1, "会議室 A", "ホワイトボード有り"));
//			 roomTypeList.add(new RoomType(2, "会議室 B", "ホワイトボード無し"));
//			 roomTypeList.add(new RoomType(3, "視聴覚室", "プロジェクター有り"));
//			 roomTypeList.add(new RoomType(4, "多目的ルーム", "フローリング"));
//			 return roomTypeList;
//
//		}
//		
}

/*
 * @PostMapping
	public String bookingPost (
			BookingFormItem bookingFormItem,
			Model model){
		//施設利用規約への同意がされていない場合、フォームを再表示
		if (!bookingFormItem.getAgreement()) {
			model.addAttribute("roomTypeList", getRoomTypeList());
			return "booking";
		}
		//
		return "bookingDone";
	}
 */
