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
    
    public partial class ORDERPRODUCT
    {
        public System.Guid ORDERID { get; set; }
        public System.Guid PRODUCTID { get; set; }
        public Nullable<int> QUANTITY { get; set; }
        public decimal PRICE { get; set; }
    
        public virtual ORDER ORDER { get; set; }
        public virtual PRODUCT PRODUCT { get; set; }
    }
}