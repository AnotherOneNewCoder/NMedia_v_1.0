package ru.netology.nmedia.activity
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.PopupMenu
//
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import ru.netology.nmedia.R
//import ru.netology.nmedia.activity.NewPostFragment.Companion.stringArg
//
//import ru.netology.nmedia.databinding.FragmentDetailsPostBinding
//
//import ru.netology.nmedia.service.Convert
//import ru.netology.nmedia.viewmodel.PostViewModelGson
//import ru.netology.nmedia.viewmodel.PostViewModelSQLImpl
//
//class DetailsPostFragment: Fragment() {
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val binding = FragmentDetailsPostBinding.inflate(layoutInflater)
//        val viewModel: PostViewModelSQLImpl by viewModels(
//            ownerProducer = ::requireParentFragment
//        )
//
//        val idRecied = arguments?.stringArg?.toLong()
//
//        viewModel.data.observe(viewLifecycleOwner) { posts ->
//             binding.postContent.apply {
//                 posts.map { post ->
//                     if (post.id == idRecied) {
//                         ivAvatar.setImageResource(R.drawable.ic_launcher_netology_foreground)
//
//                         tvpublished.text = post.published
//                         tvContent.text = post.content
//                         tvAuthor.text = post.author
//
//                         tvAmountViews.text = Convert.toConvert(post.countViews)
//
//                         ivLikes.isChecked = post.likedByMe
//                         ivLikes.text = Convert.toConvert(post.countLikes)
//                         ivReposts.isChecked = post.sharedByMe
//                         ivReposts.text = Convert.toConvert(post.countShares)
////                         if (!post.videoLink.isEmpty()) {
////                             link.text = post.videoLink
////                             groupEditor.visibility = View.VISIBLE
////                             videoLink.setOnClickListener {
////                                 val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoLink))
////                                 ContextCompat.startActivity(it.context, intent, null)
////                             }
////                             playVideo.setOnClickListener {
////                                 val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoLink))
////                                 ContextCompat.startActivity(it.context, intent, null)
////
////
////                             }
////
////                         } else {
////                             groupEditor.visibility = View.GONE
////                         }
//
//
//                         ivLikes.setOnClickListener {
//                             viewModel.likeById(post.id)
//
//                         }
//                         ivReposts.setOnClickListener {
//                             viewModel.shareById(post.id)
//                             val intent = Intent().apply {
//                                 action = Intent.ACTION_SEND
//                                 putExtra(Intent.EXTRA_TEXT, post.content)
//                                 type = "text/plain"
//                             }
//                             val startIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
//                             startActivity(startIntent)
//                         }
//
//
//                         ibMenu.setOnClickListener {
//                             val popmenu = PopupMenu(it.context, it)
//                             popmenu.apply {
//                                 inflate(R.menu.post_options)
//                                 setOnMenuItemClickListener { itemMenu ->
//                                     ibMenu.isChecked = true
//                                     when (itemMenu.itemId) {
//                                         R.id.remove -> {
//                                             viewModel.removeById(post.id)
//                                             findNavController().navigateUp()
//                                             true
//                                         }
//                                         R.id.edit -> {
//                                             viewModel.edit(post)
//                                             findNavController().navigate(R.id.action_detailsPostFragment_to_newPostFragment,
//                                             Bundle().apply
//                                              {
//                                                  stringArg = post.content
//                                              })
//                                             true
//                                         }
//                                         else -> {
//
//                                             false
//                                         }
//                                     }
//                                 }
//
//
//                             }.show()
//                             popmenu.setOnDismissListener {
//                                 ibMenu.isChecked = false
//                             }
//                         }
//                     }
//                 }
//             }
//
//
//
//
//        }
//
//
//
//        return binding.root
//    }
//}