/* checksum : 503661f7b24bb4f4c8b62738414d9b70 */
@cds.external : true
@cds.persistence.skip : true
@Capabilities.DeleteRestrictions.Deletable : false
@Capabilities.InsertRestrictions.Insertable : false
@Capabilities.UpdateRestrictions.Updatable : false
entity CatalogService.Books {
  key ID : Integer not null;
  title : LargeString;
  stock : Integer;
};

@cds.external : true
service CatalogService {};

