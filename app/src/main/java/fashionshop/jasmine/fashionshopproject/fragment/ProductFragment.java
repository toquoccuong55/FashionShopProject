package fashionshop.jasmine.fashionshopproject.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.activity.OrderActivity;
import fashionshop.jasmine.fashionshopproject.adapter.ProductsAdapter;
import fashionshop.jasmine.fashionshopproject.customView.GridSpacingItemDecoration;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.OrderItemManagement;
import fashionshop.jasmine.fashionshopproject.model.CategoryItem;
import fashionshop.jasmine.fashionshopproject.model.Products;
import fashionshop.jasmine.fashionshopproject.room.entities.OrderItemEntity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {
    private static final String CATEGORY_ITEM = "CATEGORY_ITEM";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int ARGUMENT_SPAN_COUNT = 2;
    private static final String BUNDLE_PRODUCT_OBJECT = "PRODUCT_OBJECT";
    private static final String BUNDLE_ORDER_ITEM = "ORDER_ITEM";
    private OrderItemManagement mOrderItemManagement;
    public List<OrderItemEntity> mShoppingCart;
    private List<String> size;
    private RecyclerView mRecyclerViewProduct;
    public ProductsAdapter mProductsAdapter;
    private ArrayList<Products> mProducts;
    private CategoryItem mCategoryItem;


    private OnFragmentInteractionListener mListener;


    public ProductFragment() {
        // Required empty public constructor
    }


    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void initialView() {
        mRecyclerViewProduct = (RecyclerView) getView().findViewById(R.id.recycle_view_product);
        mRecyclerViewProduct.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), ARGUMENT_SPAN_COUNT);
        mRecyclerViewProduct.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp10);
        mRecyclerViewProduct.addItemDecoration(new GridSpacingItemDecoration(ARGUMENT_SPAN_COUNT, spacingInPixels, true));
        if (mProducts != null) {
            setDrinkAdapter();
        }
    }

    private void initialData() {
//        mShoppingCart = getOrderActivity().mShoppingCart;
//        if (mShoppingCart == null) {
//            mShoppingCart = new ArrayList<>();
//        }

       // mProducts = (ArrayList<Products>) mCategoryItem.getProductList();
//        setQuantityForEachProduct();
        mShoppingCart = getOrderActivity().mShoppingCart;
        if (mShoppingCart == null) {
            mShoppingCart = new ArrayList<>();
        }
        mProducts = (ArrayList<Products>) mCategoryItem.getProductList();
        setQuantityForEachProduct();
        getOrderActivity().showCheckOutCartButton();
    }

    private void setQuantityForEachProduct() {
        for (OrderItemEntity orderItem : mShoppingCart) {
            //find product in List to set quantity
            for (Products product : mProducts) {
                if (orderItem.getProducts().getProductId() == product.getProductId()) {
                    int quantity = product.getProductQuantity() +
                            orderItem.getProducts().getProductQuantity();
                    product.setProductQuantity(quantity);
                    break;
                }
            }
        }
    }
    private void setDrinkAdapter() {
        if (mProductsAdapter == null) {
            mProductsAdapter = new ProductsAdapter(mProducts, getOrderActivity());
            mRecyclerViewProduct.setAdapter(mProductsAdapter);
            mProductsAdapter.setOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int position) {
                    try {
                        addBottomSheet((Products) mProducts.get(position).clone(), position);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            mProductsAdapter.notifyDataSetChangeCustom(mProducts);
        }
    }

    private void addBottomSheet(Products products, final int position) {
        final OrderDetailFragment orderDetailFragment = OrderDetailFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_PRODUCT_OBJECT, products);
        orderDetailFragment.setArguments(bundle);
        orderDetailFragment.show(getFragmentManager(), "Show bottom sheet diaplog product detail");
        orderDetailFragment.setOnClickCart(new OrderDetailFragment.OnClickListener() {
            @Override
            public void onClick() {
                clickToCart(orderDetailFragment, position);
            }
        });

    }
    private OrderActivity getOrderActivity() {
        return ((OrderActivity) getActivity());
    }
    private void clickToCart(OrderDetailFragment orderDetailFragment, int position) {
        Bundle bundle = orderDetailFragment.getArguments();
        Products products = (Products) bundle.getSerializable(BUNDLE_ORDER_ITEM);
        String orderItemId = UUID.randomUUID().toString();
        OrderItemEntity orderItemEntity = new OrderItemEntity(orderItemId, products);
        getOrderActivity().mShoppingCart.add(orderItemEntity);

        if(mOrderItemManagement == null){
            mOrderItemManagement = new OrderItemManagement(getActivity().getApplication());
        }
        mOrderItemManagement.addOrderItem(orderItemEntity);
        for (Products product : mProducts) {
            if (orderItemEntity.getProducts().getProductId() == product.getProductId()) {
                int quantity = product.getProductQuantity() +
                        orderItemEntity.getProducts().getProductQuantity();
                product.setProductQuantity(quantity);
                break;
            }
        }
        setDrinkAdapter();
        getOrderActivity().showCheckOutCartButton();
    }


    private void getInitialIntent() {
        Bundle bundle = getArguments();
        mCategoryItem = (CategoryItem) bundle.getSerializable(CATEGORY_ITEM);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialView();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInitialIntent();
        initialData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
