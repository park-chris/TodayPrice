package com.crystal.todayprice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.crystal.todayprice.adapter.ItemAdapter
import com.crystal.todayprice.adapter.MarketAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMainBinding
import com.crystal.todayprice.repository.PriceRepositoryImpl
import com.crystal.todayprice.ui.market.MarketActivity
import com.crystal.todayprice.viewmodel.PriceViewModel

class MainActivity : BaseActivity(ToolbarType.MENU) {
    private lateinit var binding: ActivityMainBinding

    private val priceViewModel: PriceViewModel by viewModels {
        PriceViewModel.PriceViewModelFactory(PriceRepositoryImpl())
    }

    private lateinit var adapter: MarketAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        adapter = MarketAdapter { market ->
            moveToMarketActivity(market)
        }
    }

    override fun onResume() {
        super.onResume()

        setMarketList(testMarketList)
    }

    private fun moveToMarketActivity(market: Market) {
        val intent = Intent(this, MarketActivity::class.java)
        intent.putExtra(MarketActivity.MARKET_NAME, market)
        startActivity(intent)
    }

    private fun setMarketList(markets: List<Market>) {
        binding.marketRecyclerView.adapter = adapter
        adapter.submitList(markets)
    }
}

val testMarketList = listOf<Market>(
    Market("1", "성대전통시장", "https://pbs.twimg.com/media/EA9UJBjU4AAdkCm.jpg", "서울"),
    Market("2", "인헌시장", "https://img1.daumcdn.net/thumb/C500x500/?fname=http://t1.daumcdn.net/brunch/service/guest/image/jhenA17EwA0hMPpjb9z2iEx2--0.png", "부산"),
    Market("3", "광장시장", "https://www.boardlife.co.kr/wys2/file_attach/2015/10/14/1444752096-91.jpg", "대구"),
    Market("4", "통인시장", "https://i.pinimg.com/236x/53/c8/a3/53c8a3f0f62fda9647fd389be212806e.jpg", "대전"),
    Market("5", "창신골목시장", "https://mblogthumb-phinf.pstatic.net/MjAyMDA0MTlfMjM1/MDAxNTg3Mjk2OTQ5NTE4.TslGG4Cid6mXvNCeYQ49JJHynult0l9YBrFx-cwhLNYg.vrXPyrcydJSExO58uZX9v222F9nC6rbRm510fvEmYz0g.JPEG.annie34777/1587296947254.jpg?type=w800", "여수"),
    Market("6", "금남시장", "https://opgg-com-image.akamaized.net/attach/images/20210701111239.790116.jpg", "강원도"),
    Market("7", "뚝도시장", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ3PchseONVxO5yopGpyGUYuSmggOJNMCl93uLE2M4xLxM8nhacq4ey7-odE5UzJqSi4hE&usqp=CAU", "전주"),
    Market("8", "성동용답상가시장", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPf6nqp-39drevrFygCrtbNi1BDoNleFqif_bMExVG7zVngZLw7xqyDJHPZ_J1OYXp3hE&usqp=CAU", "독도"),
)