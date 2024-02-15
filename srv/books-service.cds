using { CatalogService as external } from './external/CatalogService';

service BooksService {
    @readonly
    entity Books as projection on external.Books 
        actions {
            
        };

    action createBook(book: Books) returns Books;    
}