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
    
    public partial class SERVICE
    {
        public SERVICE()
        {
            this.REVIEWS = new HashSet<REVIEW>();
            this.SERVICEORDERs = new HashSet<SERVICEORDER>();
        }
    
        public System.Guid? ID { get; set; }
        public System.Guid? CATEGORYID { get; set; }
        public string NAME { get; set; }
        public System.Guid? SELLERID { get; set; }
        public Nullable<bool> ISACTIVE { get; set; }
        public string DETAILS { get; set; }
        public string TAG1 { get; set; }
        public string TAG2 { get; set; }
        public string TAG3 { get; set; }
        public string PLACE { get; set; }
        public string CITY { get; set; }
        public string STATE { get; set; }
        public int PINCODE { get; set; }
        public decimal? PRICE { get; set; }
        public decimal AVGRATING { get; set; }
        public int NUMBEROFUSERS { get; set; }

        public virtual CATEGORy CATEGORy { get; set; }
        public virtual ICollection<REVIEW> REVIEWS { get; set; }
        public virtual ICollection<SERVICEORDER> SERVICEORDERs { get; set; }
        public virtual USER USER { get; set; }
    }
}
