using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using APIShopKaro.Models;

namespace APIShopKaro.Services
{
    public class CommonService
    {
        /// <summary>
        /// Add new product/ service category
        /// </summary>
        /// <param name="category"></param>
        /// <returns></returns>
        public Guid AddNewCategory(CATEGORy category)
        {
            try
            {
                if(category == null)
                    throw new ArgumentNullException("Category", "Category can not be null");

                // check if all required fields are present
                if(category.NAME == null || !category.SERVICE.HasValue)
                    throw new ArgumentException("Category", "Some mandatory parameters required to add a new category are missing");

                if (!category.ID.HasValue || category.ID.Value == Guid.Empty)
                    category.ID = Guid.NewGuid();

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.CATEGORIES.Add(category);
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

                return category.ID.Value;
            }
            catch (Exception e)
            {
                throw;
            }

        }

        /// <summary>
        /// Get all product/ service categories
        /// </summary>
        /// <param name="isService">categories for service if true; else for product</param>
        /// <returns></returns>
        public List<CATEGORy> GetAllCategories(bool isService)
        {
            List<CATEGORy> categories = new List<CATEGORy>();
            try
            { 
               using(APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        categories = (from c in db.CATEGORIES
                                                     where c.SERVICE.Value == isService
                                                     select c).ToList();
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
                return categories;
            }
            catch(Exception e)
            {
                throw;
            }
        }

        // TODO : TO REVISIT User should be able to add 1 review (rating and comment) for a product/ service
        /// <summary>
        /// Add a new review for a product/ service by a user
        /// </summary>
        /// <param name="userReview"></param>
        /// <returns></returns>
        public Guid AddNewReview(REVIEW userReview)
        {
            try
            {
                if (userReview == null)
                    throw new ArgumentNullException("UserReview", "UserReview can not be null");

                if((!userReview.PRODUCTID.HasValue || userReview.PRODUCTID.Value == Guid.Empty) && (!userReview.SERVICEID.HasValue || userReview.SERVICEID.Value == Guid.Empty))
                    throw new ArgumentException("Both PRODUCTID SERVICEID can not be null together", "UserReview");

                if ((userReview.PRODUCTID.HasValue && userReview.PRODUCTID.Value != Guid.Empty) && (userReview.SERVICEID.HasValue && userReview.SERVICEID.Value != Guid.Empty))
                    throw new ArgumentException("Both PRODUCTID SERVICEID can have values together", "UserReview");

                if(!userReview.REVIEWERID.HasValue || userReview.REVIEWERID.Value == Guid.Empty)
                    throw new ArgumentException("REVIEWERID can not be null", "UserReview");

                if (userReview.RATING == null && string.IsNullOrEmpty(userReview.COMMENTS))
                    return Guid.Empty;

                using (APIShopKaro.Models.apsteamCFHEntities db = new APIShopKaro.Models.apsteamCFHEntities())
                {
                    try
                    {
                        db.REVIEWS.Add(userReview);
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
                return userReview.ID.Value;
            }
            catch(Exception e)
            {
                throw;
            }
        }
    }
}