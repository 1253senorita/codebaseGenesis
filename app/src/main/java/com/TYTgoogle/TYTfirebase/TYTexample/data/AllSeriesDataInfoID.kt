
package com.TYTgoogle.TYTfirebase.TYTexample.data

import com.TYTgoogle.TYTfirebase.TYTexample.R // 실제 R 클래스 경로 확인 및 import

// SeriesInfo, ActionItem, ActionType 클래스 import (필요시)
// 예: import com.TYTgoogle.TYTfirebase.TYTexample.model.SeriesInfo

val allSeriesData: List<SeriesInfo> = listOf(
    SeriesInfo(
        id = "BEAUTY_HEALTH01",
        displayName = "💄 뷰티/헬스케어",
        imageUrl = null,
        iconResId = R.drawable.image01_ic, // 예시: 실제 아이콘으로 교체
        initialActions = listOf(
            ActionItem(displayText = "시술/상담 예약", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "bh_reservation_new"),
            ActionItem(displayText = "내 예약 확인", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "bh_reservation_list"),
            ActionItem(displayText = "포트폴리오 관리", actionType = ActionType.UPLOAD_DATA, uploadDataType = "bh_portfolio"),
            ActionItem(displayText = "구독 멤버십 안내", actionType = ActionType.SHOW_INFO_DIALOG),
            ActionItem(displayText = "고객 리뷰 보기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "bh_customer_reviews"),
            ActionItem(displayText = "1:1 온라인 상담", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "bh_online_consult"),
            ActionItem(displayText = "제품/서비스 소개", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "bh_product_info"),
            ActionItem(displayText = "매장 위치/정보", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "bh_store_details")
        )
    ),
    SeriesInfo(
        id = "PET_CARE01",
        displayName = "🐾 반려동물",
        imageUrl = null,
        iconResId = R.drawable.image02_ic, // 예시
        initialActions = listOf(
            ActionItem(displayText = "애견 호텔/유치원 예약", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "pet_hotel_booking"),
            ActionItem(displayText = "미용 서비스 신청", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "pet_grooming_request"),
            ActionItem(displayText = "반려동물 용품 주문", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "pet_supplies_shop"),
            ActionItem(displayText = "훈련 프로그램 안내", actionType = ActionType.SHOW_INFO_DIALOG),
            ActionItem(displayText = "건강 상담 게시판", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "pet_health_forum"),
            ActionItem(displayText = "산책 친구 찾기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "pet_walking_buddy"),
            ActionItem(displayText = "입양/분양 정보", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "pet_adoption_info"),
            ActionItem(displayText = "우리 아이 자랑하기", actionType = ActionType.UPLOAD_DATA, uploadDataType = "pet_ 자랑_photo")
        )
    ),
    SeriesInfo(
        id = "CAFE_DESSERT01",
        displayName = "☕ 카페/디저트",
        imageUrl = null,
        iconResId = R.drawable.image03_ic, // 예시
        initialActions = listOf(
            ActionItem(displayText = "테이크아웃 주문", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "cafe_takeout_order"),
            ActionItem(displayText = "나만의 메뉴 만들기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "cafe_custom_menu"),
            ActionItem(displayText = "오늘의 디저트 라인업", actionType = ActionType.SHOW_INFO_DIALOG),
            ActionItem(displayText = "스탬프 적립/사용", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "cafe_loyalty_points"),
            ActionItem(displayText = "단체 주문 문의", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "cafe_group_order_qna"),
            ActionItem(displayText = "MD 상품 둘러보기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "cafe_merchandise_view"),
            ActionItem(displayText = "리뷰 이벤트 참여", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "cafe_review_event"),
            ActionItem(displayText = "매장 소식/공지", actionType = ActionType.UPLOAD_DATA, uploadDataType = "cafe_store_news")
        )
    ),
    SeriesInfo(
        id = "EDUCATION_TECH01",
        displayName = "🎓 교육/에듀테크",
        imageUrl = null,
        iconResId = R.drawable.image02_ic, // 예시
        initialActions = listOf(
            ActionItem(displayText = "온라인 강의 수강", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "edu_online_lecture"),
            ActionItem(displayText = "학습 자료실", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "edu_learning_materials"),
            ActionItem(displayText = "1:1 튜터 연결", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "edu_tutor_matching"),
            ActionItem(displayText = "수강 계획 설정", actionType = ActionType.SHOW_INFO_DIALOG),
            ActionItem(displayText = "진도 현황 확인", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "edu_progress_check"),
            ActionItem(displayText = "과제 제출/피드백", actionType = ActionType.UPLOAD_DATA, uploadDataType = "edu_assignment_submit"),
            ActionItem(displayText = "스터디 그룹 참여", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "edu_study_group"),
            ActionItem(displayText = "수강 문의/FAQ", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "edu_qna_faq")
        )
    ),
    SeriesInfo(
        id = "FOOD_DELIVERY_SPECIAL01",
        displayName = "🍗 외식/배달 전문",
        imageUrl = null,
        iconResId = R.drawable.image05_ic, // 예시
        initialActions = listOf(
            ActionItem(displayText = "빠른 배달 주문", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fd_quick_order"),
            ActionItem(displayText = "인기 메뉴 보기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fd_popular_menu"),
            ActionItem(displayText = "실시간 배달 추적", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fd_delivery_tracking"),
            ActionItem(displayText = "주문 내역 확인", actionType = ActionType.SHOW_INFO_DIALOG),
            ActionItem(displayText = "리뷰 작성하기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fd_write_review"),
            ActionItem(displayText = "쿠폰함/포인트", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fd_coupon_points"),
            ActionItem(displayText = "자주 묻는 질문", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fd_faq"),
            ActionItem(displayText = "고객센터 연결", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fd_customer_service")
        )
    ),
    SeriesInfo(
        id = "RETAIL_LIFESTYLE01",
        displayName = "🧴 리테일/생활용품",
        imageUrl = null,
        iconResId = R.drawable.ic_launcher_round, // 예시
        initialActions = listOf(
            ActionItem(displayText = "온라인 스토어", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "retail_online_store"),
            ActionItem(displayText = "신상품 보기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "retail_new_arrivals"),
            ActionItem(displayText = "매장 재고 확인", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "retail_stock_check"),
            ActionItem(displayText = "정기 배송 신청", actionType = ActionType.SHOW_INFO_DIALOG),
            ActionItem(displayText = "상품 Q&A", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "retail_product_qna"),
            ActionItem(displayText = "AS/교환/반품 안내", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "retail_customer_support"),
            ActionItem(displayText = "나만의 위시리스트", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "retail_wishlist"),
            ActionItem(displayText = "이벤트/프로모션", actionType = ActionType.UPLOAD_DATA, uploadDataType = "retail_event_promo_upload") // 운영자가 업로드
        )
    ),
    SeriesInfo(
        id = "IT_DIGITAL_SERVICE01",
        displayName = "🖥️ IT/디지털 서비스",
        imageUrl = null,
        iconResId = R.drawable.splash_icon, // 예시
        initialActions = listOf(
            ActionItem(displayText = "서비스 신청/구독", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "it_service_subscribe"),
            ActionItem(displayText = "나의 서비스 관리", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "it_my_services"),
            ActionItem(displayText = "사용량/통계 보기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "it_usage_stats"),
            ActionItem(displayText = "API 문서/가이드", actionType = ActionType.SHOW_INFO_DIALOG),
            ActionItem(displayText = "기술 지원 요청", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "it_tech_support"),
            ActionItem(displayText = "업데이트 내역", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "it_update_history"),
            ActionItem(displayText = "플랫폼 현황 대시보드", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "it_platform_dashboard"),
            ActionItem(displayText = "새로운 기능 제안", actionType = ActionType.UPLOAD_DATA, uploadDataType = "it_feature_suggestion")
        )
    ),
    SeriesInfo(
        id = "ECOMMERCE_SHOP01",
        displayName = "🛍️ 쇼핑몰/이커머스",
        imageUrl = null,
        iconResId = R.drawable.image03_ic, // 예시
        initialActions = listOf(
            ActionItem(displayText = "상품 검색/카테고리", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_search_category"),
            ActionItem(displayText = "오늘의 특가/딜", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_today_deal"),
            ActionItem(displayText = "장바구니/결제", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_cart_payment"),
            ActionItem(displayText = "주문/배송 조회", actionType = ActionType.SHOW_INFO_DIALOG),
            ActionItem(displayText = "상품 리뷰 보기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_product_reviews"),
            ActionItem(displayText = "판매자 문의하기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_seller_contact"),
            ActionItem(displayText = "개인화 추천 상품", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_personalized_recommend"),
            ActionItem(displayText = "라이브 커머스 알림", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_live_commerce_noti")
        )
    )
)
