using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIShopKaro.Models;

namespace APIShopKaro.Services
{
    public class ProductsService
    {
        // TODO : how to add image url
        /// <summary>
        /// Add new product
        /// </summary>
        /// <param name="product"></param>
        /// <returns></returns>
        public Guid AddNewProduct(PRODUCT product)
        {
            try
            {
                if (product == null)
                    throw new ArgumentNullException("Product", "Product can not be null");

                // check if all required fields are present
                if ((!product.CATEGORYID.HasValue || product.CATEGORYID.Value == Guid.Empty)|| product.NAME == null || 
                    !product.PRICE.HasValue || (!product.SELLERID.HasValue || product.SELLERID.Value == Guid.Empty))
                    throw new ArgumentException("Some mandatory parameters required to add a new product are missing", "Product");
                                
                if (!product.ID.HasValue || product.ID.Value == Guid.Empty)
                    product.ID = Guid.NewGuid();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.PRODUCTS.Add(product);
                        db.SaveChanges();
                    }
                    catch (System.Data.DataException e)
                    {
                        throw new Exception(e.InnerException.InnerException.Message);
                    }
                    catch (Exception e)
                    {
                        throw;
                    }
                }

                return product.ID.Value;
            }
            catch (Exception e)
            {
                throw;
            }
        }
    }
}