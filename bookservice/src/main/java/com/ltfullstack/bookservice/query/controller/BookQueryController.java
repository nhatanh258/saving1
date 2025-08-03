package com.ltfullstack.bookservice.query.controller;

import com.ltfullstack.bookservice.query.model.BookResponseModel;
import com.ltfullstack.bookservice.query.queries.GetAllBookQuery;
//import com.ltfullstack.commonservice.model.BookResponseCommonModel;
//import com.ltfullstack.commonservice.queries.GetBookDetailQuery;
//import com.ltfullstack.commonservice.services.KafkaService;
import com.ltfullstack.bookservice.query.queries.GetBookDetailQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

//    @Autowired
//    private KafkaService kafkaService;

    @GetMapping
    public List<BookResponseModel> getAllBooks(){
        GetAllBookQuery query = new GetAllBookQuery();
        //join() sẽ chặn (block) luồng hiện tại cho đến khi có kết quả → tức là đồng bộ hóa kết quả
        return  queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();

        //neu muon xu ly bat dong bo hay lam theo cach sau (Asynchronous):

//        queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class))
//                .thenAccept(result -> {
//                    // Xử lý kết quả khi có, không block thread
//                })
//                .exceptionally(ex -> {
//                    // Xử lý lỗi
//                    return null;
//                });

    }

    @GetMapping("{bookId}")
    public BookResponseModel getBookDetail(@PathVariable String bookId){
        GetBookDetailQuery query = new GetBookDetailQuery(bookId);
        return queryGateway.query(query,ResponseTypes.instanceOf(BookResponseModel.class)).join();
    }

//    @PostMapping("/sendMessage")
//    public void sendMessage(@RequestBody String message){
//        kafkaService.sendMessage("test",message);
//    }
}
