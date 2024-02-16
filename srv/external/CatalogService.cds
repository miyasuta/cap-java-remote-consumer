/* checksum : 293989577f835270c4e9059efd5aca20 */
@cds.external : true
@cds.persistence.skip : true
entity CatalogService.Books {
  key ID : Integer not null;
  title : LargeString;
  stock : Integer;
};

@cds.external : true
service CatalogService {};

