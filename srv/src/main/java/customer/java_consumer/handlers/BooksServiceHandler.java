package customer.java_consumer.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.cds.Result;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnAnalyzer;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.reflect.CdsModel;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.booksservice.Books;
import cds.gen.booksservice.BooksService_;
import cds.gen.booksservice.Books_;
import cds.gen.booksservice.CreateBookContext;
import cds.gen.booksservice.UpdateStockContext;
import cds.gen.catalogservice.CatalogService_;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@ServiceName(BooksService_.CDS_NAME)
public class BooksServiceHandler implements EventHandler{
    @Autowired
    @Qualifier(CatalogService_.CDS_NAME)
    CqnService catalogService;

    private final CqnAnalyzer analyzer;

    BooksServiceHandler(CdsModel model) {
        this.analyzer = CqnAnalyzer.create(model);
    }

    Logger logger = LoggerFactory.getLogger(BooksServiceHandler.class);

    // @On(event = CqnService.EVENT_READ, entity = Books_.CDS_NAME)
    // public Result readBooks(CdsReadEventContext context) {
    //     logger.info("read handler called");
    //     return catalogService.run(context.getCqn());
    // }

    @On(event = CqnService.EVENT_READ, entity = Books_.CDS_NAME)
    public void readBooks(CdsReadEventContext context) {
        logger.info("read handler called");
        logger.info(context.getCqn().toString());
        context.setResult(catalogService.run(context.getCqn()));
    }

    @On(event = CreateBookContext.CDS_NAME)
    public void createBook(CreateBookContext context) {
        logger.info("createBook handler called");
        Books book = context.getBook();
        CqnInsert insert = Insert.into("CatalogService.Books").entry(book);
        Result result = catalogService.run(insert);
        Books createdBook = result.single(Books.class);
        context.setResult(createdBook);      
    }

    @On(event = UpdateStockContext.CDS_NAME)
    public void updateStock(UpdateStockContext context) {
        logger.info("updateStock handler called");
        Books book = Books.create();
        Integer bookId = (Integer) analyzer.analyze(context.getCqn()).targetKeys().get(Books.ID);
        book.setId(bookId);
        book.setStock(context.getStock());

        CqnUpdate update = Update.entity("CatalogService.Books").data(book);
        catalogService.run(update);
        context.setCompleted();
    }
}
