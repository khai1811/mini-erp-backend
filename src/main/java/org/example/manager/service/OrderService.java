package org.example.manager.service;

import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.example.manager.dto.response.OrderRequest;
import org.example.manager.entity.Customer;
import org.example.manager.entity.Order;
import org.example.manager.entity.OrderItem;
import org.example.manager.entity.Product;
import org.example.manager.repository.CustomerRepository;
import org.example.manager.repository.OrderRepository;
import org.example.manager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // Tái sử dụng để trừ kho và lưu log kho
    @Autowired
    private InventoryService inventoryService;

    // =====================================================
    // TẠO ĐƠN HÀNG + TRỪ KHO TỰ ĐỘNG
    // =====================================================
    @Transactional(rollbackOn = Exception.class)
    public Order createOrder(OrderRequest request) {


        // =====================================================
        // 1. KIỂM TRA KHÁCH HÀNG
        // =====================================================
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Khách hàng không tồn tại"));

        // =====================================================
        // 2. KHỞI TẠO ĐƠN HÀNG
        // =====================================================
        Order order = new Order();

        // Mã đơn hàng duy nhất
        order.setOrderCode("ORD-" + System.currentTimeMillis());

        // Gán khách hàng
        order.setCustomer(customer);

        // Phương thức thanh toán
        order.setPaymentMethod(request.getPaymentMethod());

        // Trạng thái đơn hàng
        order.setStatus("PAID");

        // Danh sách chi tiết đơn hàng
        List<OrderItem> orderDetails = new ArrayList<>();

        // Tổng tiền
        double totalSum = 0;

        // =====================================================
        // 3. DUYỆT TỪNG SẢN PHẨM TRONG GIỎ HÀNG
        // =====================================================
        for (OrderRequest.CartItem item : request.getItems()) {

            // Tìm sản phẩm
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Sản phẩm ID " + item.getProductId()
                                            + " không tồn tại"));

            // =====================================================
            // 4. TRỪ KHO TỰ ĐỘNG
            // =====================================================
            inventoryService.exportStock(
                    product.getId(),
                    item.getQuantity(), // Trừ đúng số lượng khách mua
                    "Xuất kho cho đơn hàng: " + order.getOrderCode()
            );

            // =====================================================
            // 5. TẠO CHI TIẾT ĐƠN HÀNG
            // =====================================================
            OrderItem detail = new OrderItem();

            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());

            // Lưu giá tại thời điểm mua
            detail.setPriceAtPurchase(product.getExportPrice());

            // Thành tiền = giá bán * số lượng
            double itemTotal =
                    detail.getPriceAtPurchase() * detail.getQuantity();

            // Nếu entity có field totalPrice thì mở dòng dưới
            // detail.setTotalPrice(itemTotal);

            // Cộng dồn tổng đơn hàng
            totalSum += itemTotal;

            // Thêm vào danh sách
            orderDetails.add(detail);
        }

        // =====================================================
        // 6. HOÀN THIỆN ĐƠN HÀNG
        // =====================================================
        order.setItems(orderDetails);
        order.setTotalAmount(totalSum);

        // =====================================================
        // 7. LƯU ĐƠN HÀNG
        // =====================================================
        return orderRepository.save(order);
    }
    //  ** lấy danh sách đơn hàng
    public List<Order>getAllOrder(){
        return orderRepository.findAll();
    }
    // ** lấy chi tiết 1 đơn hàng
    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Không tìm thấy đơn hàng! "));
    }
    //** cập nhật trạng thái (ví dụ : hủy đơn)
    @Transactional // đảm bảo dữ liệu lun chính xác
    public Order updateStatus(Long id , String newStatus ){
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
    // ** xóa đơn hàng
    @Transactional
    public void deleteOrder(Long id){
        Order order = getOrderById(id);
         orderRepository.delete(order);
    }

}