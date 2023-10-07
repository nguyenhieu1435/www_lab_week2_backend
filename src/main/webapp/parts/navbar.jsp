<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="index.jsp">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="control-servlet?action=emp_list">Q/lý Nhân sự</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="control-servlet?action=cust_list">Q/lý Khách hàng</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="control-servlet?action=order_list">Q/lý Hóa đơn</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="control-servlet?action=handleOpenProductListClient">D/sách sản phẩm</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="control-servlet?action=product_list">Q/lý sản phẩm</a>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Thống kê
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="control-servlet?action=handleOpenGetOrderByDate">Order theo ngày</a></li>
                    <li><a class="dropdown-item" href="getOrderByDateRange.jsp">Order theo khoảng thời gian</a></li>
                    <li><a class="dropdown-item" href="getOrderByDateRangeAndEmpId.jsp">Order theo nhân viên và khoảng thời gian</a></li>
                </ul>
            </li>
        </ul>
        <div class="my-2 my-lg-0 d-flex list-inline">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="control-servlet?action=handleOpenCart">Giỏ hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Thanh toán</a>
                </li>
            </ul>
        </div>
    </div>
</nav>