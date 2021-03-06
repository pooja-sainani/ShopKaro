//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace APIShopKaro.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class PRODUCT
    {
        public PRODUCT()
        {
            this.CARTs = new HashSet<CART>();
            this.ORDERPRODUCTS = new HashSet<ORDERPRODUCT>();
            this.REVIEWS = new HashSet<REVIEW>();
        }
    
        public System.Guid? ID { get; set; }
        public System.Guid? CATEGORYID { get; set; }
        public string NAME { get; set; }
        public decimal? PRICE { get; set; }
        public System.Guid? SELLERID { get; set; }
        public string DETAILS { get; set; }
        public string TAG1 { get; set; }
        public string TAG2 { get; set; }
        public string TAG3 { get; set; }
        public int QUANTITYSOLD { get; set; }
        public int QUANTITYAVAILABLE { get; set; }
        public string IMAGEURL { get; set; }
        public int THRESHOLDQUANTITY { get; set; }
        public decimal AVGRATING { get; set; }
    
        public virtual ICollection<CART> CARTs { get; set; }
        public virtual CATEGORy CATEGORy { get; set; }
        public virtual ICollection<ORDERPRODUCT> ORDERPRODUCTS { get; set; }
        public virtual USER USER { get; set; }
        public virtual ICollection<REVIEW> REVIEWS { get; set; }
    }
}
